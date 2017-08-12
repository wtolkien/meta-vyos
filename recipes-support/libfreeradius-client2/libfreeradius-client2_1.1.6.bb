SUMMARY = "FreeRADIUS Client Library"
HOMEPAGE = "http://freeradius.org/sub_projects/"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=3e47566c9271b786693d8a08792dbf41"

SRC_URI = "http://http.debian.net/debian/pool/main/f/freeradius-client/freeradius-client_${PV}.orig.tar.gz \
    file://fix-crosscompile-check.patch"


S = "${WORKDIR}/freeradius-client-${PV}"

SRC_URI[md5sum] = "edd4d904e802ff66d35532be1475cfa7"
SRC_URI[sha256sum] = "478bfb7ec00789af150acf6a231bc9b0731d06353c7fe36a8fd6d4d83e42a07f"


inherit autotools
