SUMMARY = "VyOS configuration/operational commands for NAT"
HOMEPAGE = "https://github.com/vyos/vyatta-nat"
SECTION = "vyos/net"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-nat.git;branch=current;protocol=https \
	  "

# snapshot from Aug 10, 2017:
SRCREV = "d078bba33249cc4099b92825eb8d9e7d4373ce67"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = " \
	vyos-cfg \
	vyos-op \
	vyos-bash \
	conntrack-tools \
	iptables \
	libxmlsimple-perl \
	libfileslurptiny-perl \
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
