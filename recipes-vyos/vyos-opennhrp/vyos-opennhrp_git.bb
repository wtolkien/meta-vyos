SUMMARY = "VyOS version of OpenNHRP"
HOMEPAGE = "https://github.com/vyos/vyos-opennhrp"
SECTION = "vyos/net"


LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://MIT-LICENSE.txt;md5=fcde66d6d3a34d2a5c48fcef0b0be04c"

SRC_URI = "git://github.com/vyos/vyos-opennhrp.git;branch=current;protocol=https \
	  "

# snapshot from Aug 11, 2017:
SRCREV = "eb8d3d05b26056f4c34f4aa39b14928afb944914"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "pkgconfig-native c-ares"
RDEPENDS_${PN} = "vyos-bash"

do_compile() {
	oe_runmake 'CC=${CC}' "CFLAGS=${CFLAGS}"
}

do_install() {
	install -d ${D}/etc/opennhrp
	install ${B}/etc/opennhrp.conf ${D}/etc/opennhrp
	install ${B}/etc/opennhrp-script ${D}/etc/opennhrp
	install ${B}/etc/racoon-ph1dead.sh ${D}/etc/opennhrp
	install ${B}/etc/racoon-ph1down.sh ${D}/etc/opennhrp
	install -d ${D}/usr/sbin
	install ${B}/nhrp/opennhrp ${D}/usr/sbin
	install ${B}/nhrp/opennhrpctl ${D}/usr/sbin
}
