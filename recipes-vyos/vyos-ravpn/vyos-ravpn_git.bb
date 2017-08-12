SUMMARY = "VyOS configuration/operational commands for remote access VPN"
HOMEPAGE = "https://github.com/vyos/vyos-ravpn"
SECTION = "vyos/net"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-ravpn.git;branch=current;protocol=https \
	  "

# snapshot from Aug 11, 2017:
SRCREV = "c5e737ba10e65ab9375e9e72a49ff2decf1807e2"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "perl vyos-cfg-vpn vyos-op-vpn vyos-cfg vyos-op vyos-bash \
	ppp xl2tpd pptpd libfreeradius-client2"

FILES_${PN} += "/opt"


# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --prefix=/opt/vyatta \
    --exec_prefix=/opt/vyatta \
	--sbindir=/opt/vyatta/sbin \
	--bindir=/opt/vyatta/bin \
	--datadir=/opt/vyatta/share \
	--sysconfdir=/opt/vyatta/etc \
	"

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

		# TODO: check the following postinst commands
# remove init of daemons that we start/stop
#for init in xl2tpd pptpd; do
	#update-rc.d -f ${init} remove >/dev/null
#done

#sed '1,/start-stop-daemon/s/start-stop-daemon --start/start-stop-daemon --start --oknodo/' -i /etc/init.d/xl2tpd

#for cfg in /etc/ipsec.d/tunnels/remote-access \
#           /etc/radiusclient-ng/radiusclient-pptp.conf \
#           /etc/radiusclient-ng/servers-pptp \
#           /etc/radiusclient-ng/radiusclient-l2tp.conf \
#           /etc/radiusclient-ng/servers-l2tp \
#           /etc/radiusclient-ng/port-id-map-ravpn \
#           /etc/ppp/secrets/chap-ravpn \
#           /etc/ppp/options.xl2tpd \
#           /etc/ppp/options.pptpd; do
#  mkdir -p ${cfg%/*}
#  touch $cfg
#done

# fix Xl2tpd init script that doesn't create its startup directory
#xl2tpd_init=/etc/init.d/xl2tpd
#if [ -f $xl2tpd_init ] &&
#    ! grep -q 'mkdir -p /var/run/xl2tpd' $xl2tpd_init ; then
#    sed -i -e '/test -x $DAEMON || exit 0/a \
#mkdir -p /var/run/xl2tpd' $xl2tpd_init
#fi

#chmod 600 /etc/ppp/secrets/chap-ravpn

#cp -f ${sysconfdir}/ravpn/radius-dictionary.microsoft \
#  /etc/radiusclient-ng/dictionary.microsoft
#cp -f /etc/radiusclient-ng/dictionary /etc/radiusclient-ng/dictionary-ravpn
#echo 'INCLUDE /etc/radiusclient-ng/dictionary.merit' \
#  >> /etc/radiusclient-ng/dictionary-ravpn
#echo 'INCLUDE /etc/radiusclient-ng/dictionary.microsoft' \
#  >> /etc/radiusclient-ng/dictionary-ravpn

#mkdir -p ${sysconfdir}/ravpn/sessions

#for s in up down; do
#  cp -f ${sysconfdir}/ravpn/ppp-ip-$s /etc/ppp/ip-$s.d/ravpn-ip-$s
#done

	else
		exit 1
	fi
}
