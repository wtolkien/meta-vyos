SUMMARY = "An API for simple XML files"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cf206df8c9fe775f1d4c484386491eac"

SRC_URI = "https://cpan.metacpan.org/authors/id/G/GR/GRANTM/XML-Simple-${PV}.tar.gz"

SRC_URI[md5sum] = "bb841dce889a26c89a1c2739970e9fbc"
SRC_URI[sha256sum] = "531fddaebea2416743eb5c4fdfab028f502123d9a220405a4100e68fc480dbf8"

BBCLASSEXTEND="native"

S = "${WORKDIR}/XML-Simple-${PV}"

inherit cpan
