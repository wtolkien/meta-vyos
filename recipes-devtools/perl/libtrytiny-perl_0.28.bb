SUMMARY = "Try::Tiny - Minimal try/catch with proper preservation of $@"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=8;endline=15;md5=13e64764203af36de92f06b57017094e"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/E/ET/ETHER/Try-Tiny-${PV}.tar.gz"

SRC_URI[md5sum] = "e2f8af601a62981aab30df15a6f47475"
SRC_URI[sha256sum] = "f1d166be8aa19942c4504c9111dade7aacb981bc5b3a2a5c5f6019646db8c146"

BBCLASSEXTEND="native"

S = "${WORKDIR}/Try-Tiny-${PV}"

inherit cpan
