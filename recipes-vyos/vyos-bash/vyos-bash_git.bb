SUMMARY = "An sh-compatible command language interpreter with VyOS extensions"
HOMEPAGE = "https://github.com/vyos/vyatta-bash"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "ncurses bison-native virtual/libiconv"
RDEPENDS_${PN} += "bash-completion"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep gettext update-alternatives

PARALLEL_MAKE = ""

bindir = "/bin"
sbindir = "/sbin"

export CC_FOR_BUILD = "${BUILD_CC}"

# set lower priority than regular bash - we want /bin/sh to point to bash
ALTERNATIVE_${PN} = "sh"
ALTERNATIVE_LINK_NAME[sh] = "${base_bindir}/sh"
ALTERNATIVE_TARGET[sh] = "${base_bindir}/vbash"
ALTERNATIVE_PRIORITY = "90"


SRC_URI = "git://github.com/vyos/vyatta-bash.git;branch=current;protocol=https \
	   file://crossfix.patch \
	  "

PR = "r1"
PV = "4.1-release+git${SRCPV}"

# snapshot from Jul 11, 2017
SRCREV = "a6109c98a25f3eeeff31c6690d6bd50953484bf9"

S = "${WORKDIR}/git"

EXTRA_AUTORECONF += "--exclude=autoheader"

# additional options to be passed to the configure script:
# TODO: disable/enable job control sometimes fails to compile. Not sure why.
EXTRA_OECONF = "\
	--disable-job-control \
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
