SUMMARY = "A simple tree object"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ab27c3cedbdb0eb6e656a8722476191a"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/R/RS/RSAVAGE/Tree-Simple-${PV}.tgz"

SRC_URI[md5sum] = "a380db6ca3ca2df65fb3118040f99e27"
SRC_URI[sha256sum] = "1745edd71b516cd570676fc1d63e1baa6019cd6132a37446b064592557500cc9"

BBCLASSEXTEND="native"

S = "${WORKDIR}/Tree-Simple-${PV}"

inherit cpan
