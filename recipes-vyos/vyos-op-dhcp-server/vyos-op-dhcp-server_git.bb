SUMMARY = "VyOS DHCP server operations mechanism"
HOMEPAGE = "https://github.com/vyos/vyatta-op-dhcp-server"
SECTION = "vyos/dhcp"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-op-dhcp-server.git;branch=current;protocol=https \
	  "

# snapshot from Aug 14, 2017:
SRCREV = "78926dc360f19d76a989609ffe5aa9e7adac2fde"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "vyos-op vyos-bash perl libfileslurp-perl "

# TODO: should RDEPEND on 'dhcp', but that causes do_rootfs to fail

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
