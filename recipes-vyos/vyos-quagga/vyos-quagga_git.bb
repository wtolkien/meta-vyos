SUMMARY = "BGP/OSPF/RIP/RIB routing daemons with VyOS / Vyatta extensions"
HOMEPAGE = "https://github.com/vyos/vyatta-quagga"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

SRC_URI = "git://github.com/vyos/vyatta-quagga.git;branch=current;protocol=https \
	  "

# snapshot from Jul 13, 2017:
SRCREV = "445c195606fa062037584e612d5bc554e5c1cc30"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "readline"
RDEPENDS_${PN} += "dpkg"

FILES_${PN} += "/usr/lib /usr/share /var/run"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep update-rc.d

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
       	--prefix=/usr \
       	--sbindir=/usr/sbin \
       	--libdir=/usr/lib/quagga \
       	--localstatedir=/var/run/quagga \
       	--sysconfdir=/etc/quagga \
       	--mandir=/usr/share/man \
       	--infodir=/usr/share/info/quagga \
       	--enable-exampledir=/usr/share/doc/quagga/examples/ \
       	--enable-vtysh \
       	--enable-ipv6 \
       	--enable-watchquagga \
       	--enable-opaque-lsa \
       	--enable-ospfclient=yes \
       	--enable-ospfapi=yes \
       	--enable-ospf-te \
       	--enable-multipath=64 \
       	--enable-user=quagga \
       	--enable-group=quagga \
       	--enable-vty-group=quaggavty \
       	--enable-configfile-mask=0640 \
       	--enable-logfile-mask=0640 \
       	--enable-rtadv \
       	--enable-gcc-rdynamic \
		--with-libpam \
		--disable-doc \
		"

#       	--enable-snmp

INSANE_SKIP_${PN} = "dev-so"


INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "vyatta-quagga"
INITSCRIPT_PARAMS_${PN} = "start 30 2 3 4 5 ."


do_install_append () {
		install -d ${D}${sysconfdir}/init.d
		install ${S}/debian/vyatta-quagga.init.d ${D}${sysconfdir}/init.d/vyatta-quagga
		install -d ${D}${sysconfdir}/pam.d
		install ${S}/debian/vyatta-quagga.quagga.pam ${D}${sysconfdir}/pam.d/quagga

		install -d ${D}${sysconfdir}/logrotate.d
		#install -d -m 775 ${D}${sysconfdir}/quagga
		#install -d -m 775 ${D}/var/log/quagga
		#install -d -m 775 ${D}/var/run/quagga

		install -d ${D}/usr/share/snmp/mibs
		install ${S}/zebra/GNOME-PRODUCT-ZEBRA-MIB \
		${D}/usr/share/snmp/mibs/GNOME-PRODUCT-ZEBRA-MIB.txt
}


# perform some post-installation actions, but only on target device, not at
# build time (some of these may be moved to build time, but 'setcap' and
# 'chgrp' may not work at build time...)
pkg_postinst_${PN} () {
		if [ x"$D" = "x" ]; then
				LOG_DIR=/var/log/quagga
				RUN_DIR=/var/run/quagga
				ETC_DIR=/etc/quagga

				mkdir -p -m 775 $LOG_DIR
				chown -R quagga:quagga $LOG_DIR
				mkdir -p -m 755 $RUN_DIR
    		chown -R quagga:quagga $RUN_DIR
				mkdir -p -m 755 $ETC_DIR
    		chown -R quagga:quaggavty $ETC_DIR

				# create template config files
				for daemon in zebra ripd ripngd ospfd ospf6d isisd bgpd ; do
						cat <<-EOF > $ETC_DIR/${daemon}.conf
log syslog
log facility local7
EOF
        		case $daemon in
								ospfd) echo "smux peer .1.3.6.1.4.1.3317.1.2.5" >>$ETC_DIR/ospfd.conf ;;
								bgpd)  echo "smux peer .1.3.6.1.4.1.3317.1.2.2" >>$ETC_DIR/bgpd.conf  ;;
								ripd)  echo "smux peer .1.3.6.1.4.1.3317.1.2.3" >> $ETC_DIR/ripd.conf ;;
						esac

						chown quagga:quaggavty $ETC_DIR/${daemon}.conf
						chmod 0640 $ETC_DIR/${daemon}.conf
				done

				touch $ETC_DIR/vtysh.conf
				chown quagga:quaggavty $ETC_DIR/vtysh.conf
				chmod 0640 $ETC_DIR/vtysh.conf
				cat <<-EOF >> $ETC_DIR/vtysh.conf
username root nopassword
EOF
		else
   			exit 1
		fi
}
