SUMMARY = "Want - A generalisation of wantarray (Perl)"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=106;endline=109;md5=8da0e9be6ad39d959526f261f80eb796"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/R/RO/ROBIN/Want-${PV}.tar.gz"

SRC_URI[md5sum] = "33b2dae5db59781b9a0434fa1db04aab"
SRC_URI[sha256sum] = "b4e4740b8d4cb783591273c636bd68304892e28d89e88abf9273b1de17f552f7"

BBCLASSEXTEND="native"

S = "${WORKDIR}/Want-${PV}"

inherit cpan
