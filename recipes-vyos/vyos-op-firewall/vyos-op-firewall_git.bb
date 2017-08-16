SUMMARY = "VyOS Operatation command templates and scripts for the firewall subsystem"
HOMEPAGE = "https://github.com/vyos/vyatta-op-firewall"
SECTION = "vyos/firewall"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-op-firewall.git;branch=current;protocol=https \
	  "

# snapshot from Aug 14, 2017:
SRCREV = "3b8f6f59659f4076ac9f222ad7c7f713d6e59edc"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "vyos-op vyos-bash vyos-cfg vyos-cfg libxslt"

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
