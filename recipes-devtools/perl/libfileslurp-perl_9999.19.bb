SUMMARY = "File::Slurp - Simple and Efficient Reading/Writing/Modifying of Complete Files"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=33;endline=37;md5=45da23a771cccedf44f6983c46194245"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/U/UR/URI/File-Slurp-${PV}.tar.gz"

SRC_URI[md5sum] = "7d584cd15c4f8b9547765eff8c4ef078"
SRC_URI[sha256sum] = "ce29ebe995097ebd6e9bc03284714cdfa0c46dc94f6b14a56980747ea3253643"

BBCLASSEXTEND="native"

S = "${WORKDIR}/File-Slurp-${PV}"

inherit cpan
