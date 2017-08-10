SUMMARY = "VyOS configuration/operational commands for Conntrack"
HOMEPAGE = "https://github.com/vyos/vyatta-conntrack"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-conntrack.git;branch=current;protocol=https \
	  "

# snapshot from Aug 10, 2017:
SRCREV = "a8873e0bbaff65a744741976018240f56106a1c3"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "vyos-cfg-system vyos-cfg vyos-op conntrack-tools"

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
