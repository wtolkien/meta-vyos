SUMMARY = "VyOS configuration/operational commands for remote access VPN"
HOMEPAGE = "https://github.com/vyos/vyos-ravpn"
SECTION = "vyos/vpn"


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

do_install_append() {
	install -d ${D}/etc/radiusclient-ng
	touch ${D}/etc/radiusclient-ng/radiusclient-pptp.conf
	touch ${D}/etc/radiusclient-ng/servers-pptp
	touch ${D}/etc/radiusclient-ng/radiusclient-l2tp.conf
	touch ${D}/etc/radiusclient-ng/servers-l2tp
	touch ${D}/etc/radiusclient-ng/port-id-map-ravpn
	install -d ${D}/etc/ppp
	touch ${D}/etc/ppp/options.xl2tpd
	touch ${D}/etc/ppp/options.pptpd
	install -d ${D}/etc/ppp/secrets
	touch ${D}/etc/ppp/secrets/chap-ravpn
	chmod 0600 ${D}/etc/ppp/secrets/chap-ravpn

	install ${S}/scripts/radius-dictionary.microsoft \
		${D}/etc/radiusclient-ng/dictionary.microsoft
	echo 'INCLUDE /etc/radiusclient-ng/dictionary.merit' \
		>> ${D}/etc/radiusclient-ng/dictionary-ravpn
	echo 'INCLUDE /etc/radiusclient-ng/dictionary.microsoft' \
		>> ${D}/etc/radiusclient-ng/dictionary-ravpn

	install -d ${D}/opt/vyatta/etc/ravpn/sessions
	install ${S}/scripts/ppp-ip-up ${D}/etc/ppp/ip-up.d/ravpn-ip-up
	install ${S}/scripts/ppp-ip-down ${D}/etc/ppp/ip-down.d/ravpn-ip-down
}
