SUMMARY = "HTTP::Message - HTTP style message"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8172a5c365057447644970fc3df6448e"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/O/OA/OALDERS/HTTP-Message-${PV}.tar.gz"

SRC_URI[md5sum] = "4c1b7c6ee114c1cff69379ec9651d9ac"
SRC_URI[sha256sum] = "f25f38428de851e5661e72f124476494852eb30812358b07f1c3a289f6f5eded"

BBCLASSEXTEND="native"

S = "${WORKDIR}/HTTP-Message-${PV}"

inherit cpan
