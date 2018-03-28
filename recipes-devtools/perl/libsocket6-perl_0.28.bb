SUMMARY = "Socket6 - IPv6 related part of the C socket.h defines and structure manipulators"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=31;endline=68;md5=aa15b0e3744ac40eaada8738eccd24df"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/U/UM/UMEMOTO/Socket6-${PV}.tar.gz \
        file://0001-libsocket6-crosscompile-workaround.patch \
        "

SRC_URI[md5sum] = "aa8489135a3dbcec6233396e1aeb043b"
SRC_URI[sha256sum] = "bfd49ab99f3197c99285fed4683c4edc06277c1e4453f593e694d7bff0974586"

BBCLASSEXTEND="native"

S = "${WORKDIR}/Socket6-${PV}"

inherit cpan
