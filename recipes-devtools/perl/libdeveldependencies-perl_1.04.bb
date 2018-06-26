SUMMARY = "Devel::Dependencies - Perl extension for examining dependencies on modules"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=25;md5=4297778f77ebc9ecf034254a3d5ad9c8"

SRC_URI = "https://cpan.metacpan.org/authors/id/N/NE/NEILB/Devel-Dependencies-${PV}.tar.gz"

SRC_URI[md5sum] = "a8dde5c7bae42801c8b8a7a6c29cc694"
SRC_URI[sha256sum] = "faffb53cfabae7cf2ec5762e10bac887daa975cbeb34cb07c386218ab03efee1"

BBCLASSEXTEND="native"

S = "${WORKDIR}/Devel-Dependencies-${PV}"

inherit cpan
