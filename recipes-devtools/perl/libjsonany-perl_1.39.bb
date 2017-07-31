SUMMARY = "JSON::Any - (DEPRECATED) Wrapper Class for the various JSON classes"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=898babc6bf65fefbd557cd3b6bc531be"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/E/ET/ETHER/JSON-Any-${PV}.tar.gz"

SRC_URI[md5sum] = "5d353625730e8a995bdee29560a2159b"
SRC_URI[sha256sum] = "ae49755cf3710a6a3276a37ab7d5c3e7e0c0aeb2dab354acd7682c09a77957c3"


BBCLASSEXTEND="native"

S = "${WORKDIR}/JSON-Any-${PV}"

inherit cpan
