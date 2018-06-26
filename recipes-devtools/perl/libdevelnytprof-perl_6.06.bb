SUMMARY = "Devel::NYTProf - Powerful fast feature-rich Perl source code profiler"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README.md;beginline=18;md5=41301c3816b2b3be7f486b489399593e"

SRC_URI = "https://cpan.metacpan.org/authors/id/T/TI/TIMB/Devel-NYTProf-${PV}.tar.gz"

SRC_URI[md5sum] = "efbc70de0e372e7f2acb12f4aa274cd7"
SRC_URI[sha256sum] = "a14227ca79f1750b92cc7b8b0a5806c92abc4964a21a7fb100bd4907d6c4be55"

BBCLASSEXTEND="native"

S = "${WORKDIR}/Devel-NYTProf-${PV}"

inherit cpan
