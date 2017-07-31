SUMMARY = "VyOS configuration migration scripts"
HOMEPAGE = "https://github.com/vyos/vyatta-config-migrate"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-config-migrate.git;branch=current;protocol=https \
	  "

# snapshot from Jul 13, 2017:
SRCREV = "a3f858e20570e9723331702d57248b5983e894f3"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "perl"

RDEPENDS_${PN} = " \
	perl \
	vyos-bash \
	"

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
