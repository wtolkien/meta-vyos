SUMMARY = "VyOS VPN operations mechanism"
HOMEPAGE = "https://github.com/vyos/vyatta-op-vpn"
SECTION = "vyos/vpn"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-op-vpn.git;branch=current;protocol=https \
	  "

# snapshot from Aug 5, 2017:
SRCREV = "cce844b42a29bc00185555bbf7a2ec989eec8d8b"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash vyos-op"
RDEPENDS_${PN} = "strongswan ncurses ntp ethtool"

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
