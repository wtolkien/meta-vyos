SUMMARY = "IO::Prompt - Interactively prompt for user input"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=40;endline=45;md5=2ab6f4aa386cc6141508f3ce45c4ca91"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/D/DC/DCONWAY/IO-Prompt-${PV}.tar.gz"

SRC_URI[md5sum] = "24d7c674fd8ec43edf8a05eeb068fc3a"
SRC_URI[sha256sum] = "f17bb305ee6ac8b5b203e6d826eb940c4f3f6d6f4bfe719c3b3a225f46f58615"

RDEPENDS_${PN} = "libtermreadkey-perl libwant-perl"

BBCLASSEXTEND="native"

S = "${WORKDIR}/IO-Prompt-${PV}"

inherit cpan
