SUMMARY = "NetAddr::IP - Manages IPv4 and IPv6 addresses and subnets"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://Copying;md5=cde580764a0fbc0f02fafde4c65d6227"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/M/MI/MIKER/NetAddr-IP-${PV}.tar.gz"

SRC_URI[md5sum] = "990dfcbffae356835c536e8ab56a2880"
SRC_URI[sha256sum] = "ec5a82dfb7028bcd28bb3d569f95d87dd4166cc19867f2184ed3a59f6d6ca0e7"

BBCLASSEXTEND="native"

S = "${WORKDIR}/NetAddr-IP-${PV}"

inherit cpan
