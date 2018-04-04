SUMMARY = "This module is a blunt rewrite of XML::Simple (by Grant McLean) to use \
		   the XML::LibXML parser for XML structures, where the original uses \
		   plain Perl or SAX parsers.LWP - The World-Wide Web library for Perl"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=20;endline=20;md5=24ef5d80edf4b39f6ddec4550c9db197"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/M/MA/MARKOV/XML-LibXML-Simple-${PV}.tar.gz"

SRC_URI[md5sum] = "402da3e6575af4fac52dc911c9935fb0"
SRC_URI[sha256sum] = "1ccc5fb166586232c939b27244abf1bb7627c7d2fbabe33a654cfcf6c7a416bd"

BBCLASSEXTEND="native"

S = "${WORKDIR}/XML-LibXML-Simple-${PV}"

RDEPENDS_${PN} = "libxml-libxml-perl \
				 "

# we use this as a replacement for XML::Simple, so we put link in the original
# place
do_install_append () {
	XMLS_FILES=`find ${D} -name Simple*`
	for f in ${XMLS_FILES}; do
		XMLS_DIR=`dirname ${f}`
		XMLS_FILE=`basename ${f}`
		cd ${XMLS_DIR}/..
		ln -f -s LibXML/${XMLS_FILE} .
	done
}

inherit cpan
