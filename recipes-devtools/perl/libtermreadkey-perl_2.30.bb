SUMMARY = "Term::ReadKey - A perl module for simple terminal control"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=1;endline=22;md5=08a052b6cb3feadfc4f940f7a3616ae9"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/J/JS/JSTOWE/TermReadKey-${PV}.tar.gz"

SRC_URI[md5sum] = "f0ef2cea8acfbcc58d865c05b0c7e1ff"
SRC_URI[sha256sum] = "8c4c70bf487f2e432046dce07cf4b77ff181667d0905f9cb1203ff95ff5dd5ff"

BBCLASSEXTEND="native"

S = "${WORKDIR}/TermReadKey-${PV}"

inherit cpan
