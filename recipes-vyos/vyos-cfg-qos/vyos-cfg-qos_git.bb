SUMMARY = "VyOS Configuration templates and scripts for QoS"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg-qos"
SECTION = "vyos/net"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg-qos.git;branch=current;protocol=https \
	  "

# snapshot from Aug 11, 2017:
SRCREV = "52f85ec00b8e85723920ebaab49ed8b318d9d475"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "sed perl procps vyos-cfg vyos-bash iproute2 bridge-utils ethtool"

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
