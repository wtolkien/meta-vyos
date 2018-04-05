SUMMARY = "VyOS DHCP server configuration mechanism"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg-dhcp-server"
SECTION = "vyos/dhcp"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg-dhcp-server.git;branch=current;protocol=https \
	  "

# snapshot from Aug 14, 2017:
SRCREV = "fc685e0d6d68fcd5fe7df81334378c6468b6597d"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = " \
	vyos-cfg \
	vyos-bash \
	sed  \
	perl \
	libnetaddrip-perl \
	libhtml-parser-perl \
	procps \
	ntp \
	sudo \
	net-snmp \
	dhcp-server \
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
