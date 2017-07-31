SUMMARY = "An sh-compatible command language interpreter with VyOS extensions"
HOMEPAGE = "https://github.com/vyos/vyatta-bash"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "ncurses"
RDEPENDS_${PN} += "bash-completion"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep gettext update-alternatives

PARALLEL_MAKE = ""

bindir = "/bin"
sbindir = "/sbin"

export CC_FOR_BUILD = "${BUILD_CC}"

ALTERNATIVE_${PN} = "sh"
ALTERNATIVE_LINK_NAME[sh] = "${base_bindir}/sh"
ALTERNATIVE_TARGET[sh] = "${base_bindir}/vbash"
ALTERNATIVE_PRIORITY = "200"


SRC_URI = "git://github.com/vyos/vyatta-bash.git;branch=current;protocol=https \
	   file://crossfix.patch \
	  "

PR = "r1"
PV = "4.1-release+git${SRCPV}"

# snapshot from Jul 11, 2017
SRCREV = "a6109c98a25f3eeeff31c6690d6bd50953484bf9"

S = "${WORKDIR}/git"

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
	--with-curses \
	--disable-net-redirections \
	--enable-largefile \
	--prefix=/usr \
	--infodir=/usr/share/info \
	--mandir=/usr/share/man \
"


do_configure () {
	gnu-configize
	oe_runconf
}

pkg_postinst_${PN} () {
	touch $D${sysconfdir}/shells
	grep -q "bin/vbash" $D${sysconfdir}/shells || echo /bin/vbash >> $D${sysconfdir}/shells
	grep -q "bin/sh" $D${sysconfdir}/shells || echo /bin/sh >> $D${sysconfdir}/shells
}


