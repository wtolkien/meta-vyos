SUMMARY = "VyOS configuration templates and scripts for eventwatchd"
HOMEPAGE = "https://github.com/vyos/vyatta-eventwatch"
SECTION = "vyos/monitor"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-eventwatch.git;branch=current;protocol=https \
	  "

# snapshot from Aug 14, 2017:
SRCREV = "a7e2b05676297b1f5e17afd3c98140dcdf67ca4b"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "vyos-eventwatchd vyos-cfg vyos-bash sudo"

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
