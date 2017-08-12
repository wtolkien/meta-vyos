SUMMARY = "VXLAN configuration templates and scripts"
HOMEPAGE = "https://github.com/vyos/vyos-vxlan"
SECTION = "vyos/net"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyos-vxlan.git;branch=current;protocol=https \
	  "

# snapshot from Aug 11, 2017:
SRCREV = "e426911045ca885a87361f257ea86bc2801e0d7b"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "vyos-cfg-system vyos-cfg vyos-op"

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
