SUMMARY = "VyOS netflow config & op templates/scripts"
HOMEPAGE = "https://github.com/vyos/vyatta-netflow"
SECTION = "vyos/net"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=16de935ebcebe2420535844d4f6faefc"

SRC_URI = "git://github.com/vyos/vyatta-netflow.git;branch=current;protocol=https \
	  "

# snapshot from Aug 14, 2017:
SRCREV = "3201ba29c4bb510440ed1141301d18c6593aa376"

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
