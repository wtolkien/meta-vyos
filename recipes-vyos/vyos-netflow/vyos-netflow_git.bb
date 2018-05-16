SUMMARY = "VyOS netflow config & op templates/scripts"
HOMEPAGE = "https://github.com/vyos/vyatta-netflow"
SECTION = "vyos/monitor"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=16de935ebcebe2420535844d4f6faefc"

SRC_URI = "git://github.com/vyos/vyatta-netflow.git;branch=current;protocol=https \
	  "

# snapshot from April 13, 2018:
SRCREV = "af326e6ae9c9f12993acdd18434446a92c874cb9"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "vyos-cfg vyos-cfg-system vyos-op perl pmacct"

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
