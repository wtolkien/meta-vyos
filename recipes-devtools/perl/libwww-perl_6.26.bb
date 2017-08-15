SUMMARY = "LWP - The World-Wide Web library for Perl"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=225d44a95fa3addb1da6d91187ab189f"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/O/OA/OALDERS/libwww-perl-${PV}.tar.gz"

SRC_URI[md5sum] = "457bf4a4f40af3e2f43653329a9c6ab7"
SRC_URI[sha256sum] = "d0c5435275f8638ff36fff8f655ad2ccad1156e66cc47bfacfb9e44fc585b24f"

BBCLASSEXTEND="native"

S = "${WORKDIR}/libwww-perl-${PV}"

RDEPENDS_${PN} = "liburi-perl \
				  libhttpdate-perl \
				  libtrytiny-perl \
				 "

inherit cpan
