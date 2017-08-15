SUMMARY = "A simple daemon that looks up regex patterns in text streams like \
		   logs or commands output and executes actions mapped to them"
SECTION = "vyos/net"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/vyos/eventwatchd.git;branch=current;protocol=https \
	  "

# snapshot from Aug 14, 2017:
SRCREV = "c3d8ae7352f3e9c65b0f05dda245d144252574ca"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "libxmlsimple-perl"


# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep
