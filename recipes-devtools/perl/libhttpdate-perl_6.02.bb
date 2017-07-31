SUMMARY = "HTTP::Date - date conversion routines"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=107;endline=111;md5=c497f7ddf8873e6eb10bae45c4967744"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/G/GA/GAAS/HTTP-Date-${PV}.tar.gz"

SRC_URI[md5sum] = "52b7a0d5982d61be1edb217751d7daba"
SRC_URI[sha256sum] = "e8b9941da0f9f0c9c01068401a5e81341f0e3707d1c754f8e11f42a7e629e333"

BBCLASSEXTEND="native"

S = "${WORKDIR}/HTTP-Date-${PV}"

inherit cpan
