SUMMARY = "VyOS 802.11 wireless services templates and scripts"
HOMEPAGE = "https://github.com/vyos/vyatta-wireless"
SECTION = "vyos/wlan"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=fcb02dc552a041dee27e4b85c7396067"

SRC_URI = "git://github.com/vyos/vyatta-wireless.git;branch=current;protocol=https \
	  "

# snapshot from Jul 28, 2017:
SRCREV = "41ca041858f552c2fa60178c6fb1e6f090db967c"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"

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
