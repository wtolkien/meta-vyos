SUMMARY = "VyOS system-level configuration templates/scripts"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg-system"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg-system.git;branch=current;protocol=https \
		  file://git/vyatta-postconfig-bootup.script \
	      "

# snapshot from Jul 13, 2017:
SRCREV = "9d71d68dac2e865838c1bb872004e0cb843b42f1"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "perl"

RDEPENDS_${PN} = " \
	sudo \
	procps \
	iproute2 \
	ethtool \
	bridge-utils \
	tcpdump \
	libpam \
	rsyslog \
	less \
	unionfs-fuse \
	debianutils \
	perl \
	perl-misc \
	perl-lib \
	perl-module-cpan \
	libioprompt-perl \
	libnetaddrip-perl \
	libsortversions-perl \
	libfilesync-perl \
	libsocket6-perl \
	libtimedate-perl \
	libfileslurp-perl \
	libjsonany-perl \
	liblwww-perl \
	libhttpmessage-perl \
	vyos-bash \
	vyos-wireless \
	vyos-cfg-quagga \
	"

# add directories that otherwise wouldn't automatically get packaged up...
FILES_${PN} += "/opt /lib /usr/share"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit cpan autotools-brokensep

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --prefix=/opt/vyatta \
    --exec_prefix=/opt/vyatta \
	--sbindir=/opt/vyatta/sbin \
	--bindir=/opt/vyatta/bin \
	--datadir=/opt/vyatta/share \
	--sysconfdir=/opt/vyatta/etc \
	"

do_install_append () {
	# copy MIB files...
	install -d ${D}/usr/share/snmp/mibs
	install ${S}/mibs/* ${D}/usr/share/snmp/mibs

	# stuff that used to be in the Debian 'postinst' script
	install -d ${D}/var/log/user
	install -d ${D}/var/core

	install -d -m 2775 ${D}/opt/vyatta/config
	install -d -m 2775 ${D}/opt/vyatta/etc/config
	install -d -m 2775 ${D}/opt/vyatta/etc/config/auth
	install -d -m 2775 ${D}/opt/vyatta/etc/config/scripts
	install -d -m 2775 ${D}/opt/vyatta/etc/config/user-data
	install -d -m 2775 ${D}/opt/vyatta/etc/config/support

	install -d ${D}/opt/vyatta/etc/logrotate
	install -d ${D}/opt/vyatta/etc/netdevice.d

	install vyatta-postconfig-bootup.script ${D}/opt/vyatta/etc/config/scripts
}

# perform some post-installation actions, but only on target device, not at
# build time (a lot of this probably should be moved to build time, for now
# they were just copied here from Debian's postinst script)
# Make sure to prepend variables with 'vy_' to avoid conflicts with bitbake
# variables
pkg_postinst_${PN} () {
	if [ x"$D" = "x" ]; then
		vy_prefix=/opt/vyatta
		vy_exec_prefix=${vy_prefix}
		vy_sysconfdir=${vy_prefix}/etc
		vy_bindir=${vy_exec_prefix}/bin
		vy_sbindir=${vy_exec_prefix}/sbin

		# get owner/group setting changes out of the way...
		chgrp vyattacfg /opt/vyatta/config
		chgrp -R vyattacfg /opt/vyatta/etc/config

		# since we need to install more than one startup script, we don't use
		# the update-rc.d.bbclass - it can only handle one script per package
		update-rc.d -s vyatta-config-reboot-params start 20 2 3 4 5 .
		update-rc.d -s vyos-intfwatchd start 95 2 3 4 5 .

		# TODO: ec2 requires dmidecode - not sure if that makes sense for ARM
		#update-rc.d -s ec2-vyos-init start 96 2 3 4 5 .

		# TODO (maybe):
		# remove init of daemons that are controlled by Vyatta configuration process
		#for init in ntp ssh snmpd openhpid logd \
        #    ipvsadm dnsmasq ddclient radvd hostapd conntrackd
		#do
  		#	update-rc.d -f ${init} remove >/dev/null
		#done

		# Remove rsyslog logrotate since it has hardcoded assumptions about syslog
		# files
		rm -f /etc/logrotate.d/rsyslog

		if [ "${vy_sysconfdir}" != "/etc" ]; then
    		touch /etc/sudoers
    		cp -p /etc/sudoers /etc/sudoers.bak

    		# enable ssh banner
    		sed -i 's/^#Banner/Banner/' /etc/ssh/sshd_config
    		# make sure PermitRoot is off
    		sed -i '/^PermitRootLogin/s/yes/no/' /etc/ssh/sshd_config
    		# make sure PasswordAuthentication is on
    		sed -i 's/^#PasswordAuthentication/PasswordAuthentication/' /etc/ssh/sshd_config
    		sed -i '/^PasswordAuthentication/s/no/yes/' /etc/ssh/sshd_config

			# TODO: remove ssh v1 support
    		# add HostKeys for protocol version 1
			if [ ! -s /etc/ssh/ssh_host_key ]; then
				echo "  generating ssh RSA1 key..."
				ssh-keygen -q -f /etc/ssh/ssh_host_key -N '' -t rsa1
			fi
			sed -i 's/^Protocol 2/Protocol 1,2/' /etc/ssh/sshd_config

    		# add UseDNS line
    		sed -i '/^UseDNS/d' /etc/ssh/sshd_config
    		echo 'UseDNS yes' >>/etc/ssh/sshd_config

    		# Turn off Debian default for %sudo
    		sed -i -e '/^%sudo/d' /etc/sudoers || true

    		# Add VyOS entries for sudoers
    		cp ${vy_sysconfdir}/sudoers /etc/sudoers.d/vyatta
    		chmod 0440 /etc/sudoers.d/vyatta

    		# purge off ancient devfs stuff from /etc/securetty
			# WT: nope, OE's default is not so ancient!
    		# cp $sysconfdir/securetty /etc/securetty

    		for f in issue issue.net; do
				if [ ! -e /etc/$f.old ]; then
            		cp ${vy_sysconfdir}/$f /etc/$f
        		fi
    		done

			mkdir -p /etc/sysctl.d
    		cp ${vy_sysconfdir}/vyatta-sysctl.conf /etc/sysctl.d/30-vyatta-router.conf

			# Set file capabilities - paths can be different from original Debian/VyOS
			# distro, so we always check in /bin /sbin /usr/bin and /usr/sbin
			sed -r -e '/^#/d' -e '/^[[:blank:]]*$/d' < ${vy_sysconfdir}/filecaps | \
			    while read capability path; do
					file_name=`basename $path`
					for p in /bin /sbin /usr/bin /usr/sbin; do
						if [ -f $p/$file_name ]; then
							# if symlink, then get symlink target. setcap doesn't work
							# on symlinks
							if [ -h $p/$file_name ]; then
							    sym_target=`readlink $p/$file_name`
								touch -c $sym_target
			       				setcap $capability $sym_target
							else
			       				touch -c $p/$file_name
			       				setcap $capability $p/$file_name
							fi
						fi
					done
			    done

			# Install pam_cap config
			cp ${vy_sysconfdir}/capability.conf /etc/security/capability.conf

			# Install our own version of rsyslog.conf without
			# default targets
			mv /etc/rsyslog.conf /etc/rsyslog.conf.orig
			cp ${vy_sysconfdir}/rsyslog.conf /etc/rsyslog.conf

			# TODO: VyOS wants full speed, embedded devices may want to be more
			# power-efficient
			# Install own version of cpufrequtils config
			#cp ${vy_sysconfdir}/cpufrequtils /etc/default/cpufrequtils
		fi

		# TODO: it seems that OE doesn't have an rc.local file to start with...
		# call vyatta-postconfig-bootup.script from /etc/rc.local
		if ! grep -q /opt/vyatta/etc/config/scripts/vyatta-postconfig-bootup.script \
		    /etc/rc.local
		then
		    cat <<EOF >>/etc/rc.local
# Do not remove the following call to vyatta-postconfig-bootup.script.
# Any boot time workarounds should be put in script below so that they
# get preserved for the new image during image upgrade.
POSTCONFIG=/opt/vyatta/etc/config/scripts/vyatta-postconfig-bootup.script
[ -x \$POSTCONFIG ] && \$POSTCONFIG
exit 0
EOF
		fi

		touch /etc/environment

		sed -i 's/^set /builtin set /' /usr/share/bash-completion/bash_completion

		# TODO: check the following:
		#dpkg-reconfigure -f noninteractive openssh-server
		#rm -f /etc/ssh/*.broken
		#update-rc.d -f ssh remove >/dev/null

		# TODO: OE has no /etc/pam.d/login file
		# Fix up PAM configuration for login so that invalid users are prompted
		# for password
		#sed -i 's/requisite[ \t][ \t]*pam_securetty.so/required pam_securetty.so/' /etc/pam.d/login

		# TODO: OE currently uses busybox 'adduser' command
		# Change default shell for new accounts
		#sed -i -e ':^DSHELL:s:/bin/bash:/bin/vbash:' /etc/adduser.conf

		# Do not allow users to change full name field (controlled by Vyatta config)
		sed -i -e 's/^CHFN_RESTRICT/#&/' /etc/login.defs

		# TODO: OE has no /etc/pam.d/passwd command
		# Only allow root to use passwd command
		#if ! grep -q 'pam_succeed_if.so' /etc/pam.d/passwd ; then
		#    sed -i -e '/^@include/i \
		#password	requisite pam_succeed_if.so user = root
		#' /etc/pam.d/passwd
		#fi

		# remove unnecessary ddclient script in /etc/ppp/ip-up.d/
		# this logs unnecessary messages trying to start ddclient
		rm -f /etc/ppp/ip-up.d/ddclient
	else
   		exit 1
	fi
}
