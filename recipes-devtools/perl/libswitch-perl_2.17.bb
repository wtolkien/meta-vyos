SUMMARY = "Switch - A switch statement for Perl, do not use if you can use given/when"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=23;endline=26;md5=5b6ee11b31aeb897eaaf818604b87cb1"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/C/CH/CHORNY/Switch-${PV}.tar.gz"

SRC_URI[md5sum] = "34e2b6dac0a43384505b4e036633cff0"
SRC_URI[sha256sum] = "31354975140fe6235ac130a109496491ad33dd42f9c62189e23f49f75f936d75"

BBCLASSEXTEND="native"

S = "${WORKDIR}/Switch-${PV}"

inherit cpan
