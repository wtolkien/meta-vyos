SUMMARY = "Sort::Versions - a perl 5 module for sorting of revision-like numbers"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=8;endline=12;md5=2b0b24ec7fa0c230c84212bd2e2e9c27"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/N/NE/NEILB/Sort-Versions-${PV}.tar.gz"

SRC_URI[md5sum] = "c975afa9dd114951d902aa4a81ead685"
SRC_URI[sha256sum] = "bf5f3307406ebe2581237f025982e8c84f6f6625dd774e457c03f8994efd2eaa"

BBCLASSEXTEND="native"

S = "${WORKDIR}/Sort-Versions-${PV}"

inherit cpan
