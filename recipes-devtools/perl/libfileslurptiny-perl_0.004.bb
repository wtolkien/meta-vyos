SUMMARY = "A simple, sane and efficient file slurper [DISCOURAGED]"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c240521efd1784c4eb557a4597fa0465"

SRC_URI = "https://cpan.metacpan.org/authors/id/L/LE/LEONT/File-Slurp-Tiny-${PV}.tar.gz"

SRC_URI[md5sum] = "7575b81543281ea57cdb7e5eb3f73264"
SRC_URI[sha256sum] = "452995beeabf0e923e65fdc627a725dbb12c9e10c00d8018c16d10ba62757f1e"

BBCLASSEXTEND="native"

S = "${WORKDIR}/File-Slurp-Tiny-${PV}"

inherit cpan
