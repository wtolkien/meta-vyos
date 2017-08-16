SUMMARY = "VyOS configuration/operational commands for OpenVPN"
HOMEPAGE = "https://github.com/vyos/vyatta-openvpn"
SECTION = "vyos/vpn"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-openvpn.git;branch=current;protocol=https \
	  "

# snapshot from Aug 1, 2017:
SRCREV = "8b2fa99ee4943f74f9ec7d5dd43ac170c85dc82c"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"

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

do_install_append () {
	install -d ${D}/opt/vyatta/etc/openvpn/ccd
	install -d ${D}/opt/vyatta/etc/openvpn/status
}
