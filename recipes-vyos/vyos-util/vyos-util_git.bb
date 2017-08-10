SUMMARY = "Various utilities written in C for speed"
HOMEPAGE = "https://github.com/vyos/vyatta-util"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-util.git;branch=current;protocol=https \
	  "

# snapshot from Aug 10, 2017:
SRCREV = "382cc5016fd9724f8552ab30d7bd4dd56a1fe111"

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

# remove files in /usr/include and /usr/lib (original VyOS package
# does not contain these files)
do_install_append () {
	rm -rf ${D}/usr
}
