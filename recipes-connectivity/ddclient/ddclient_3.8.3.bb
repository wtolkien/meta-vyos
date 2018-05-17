SUMMARY = "Dynamic DNS Update Client"
PR = "r1"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=ed4b1320404827af6b24b60c0c53656f \
                    file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

SRC_URI = "https://sourceforge.net/projects/ddclient/files/ddclient/ddclient-${PV}/ddclient-${PV}.tar.bz2 \
    file://ddclient.init \
    file://ddclient.conf \
    file://ddclient.default \
	"
SRC_URI[md5sum] = "3b426ae52d509e463b42eeb08fb89e0b"
SRC_URI[sha256sum] = "d40e2f1fd3f4bff386d27bbdf4b8645199b1995d27605a886b8c71e44d819591"

RDEPENDS_${PN}_append = " perl"

do_configure () {
	:
}

do_compile () {
	:
}

do_install () {
    install -d ${D}${sysconfdir}
    install -m 0600 ${WORKDIR}/ddclient.conf ${D}${sysconfdir}

    install -d ${D}${sysconfdir}/default
    install -m 0600 ${WORKDIR}/ddclient.default ${D}${sysconfdir}/default/ddclient

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/ddclient.init ${D}${sysconfdir}/init.d/ddclient

	install -d ${D}${sbindir}
	install -m 0755 ${S}/ddclient ${D}${sbindir}

    install -d ${D}${localstatedir}/ddclient
}
