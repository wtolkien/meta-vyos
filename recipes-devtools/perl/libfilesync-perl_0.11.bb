SUMMARY = "File::Sync - Perl access to fsync() and sync() function calls"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://README;beginline=39;endline=45;md5=50b894e78e3f901debf301a00010e2f6"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/B/BR/BRIANSKI/File-Sync-${PV}.tar.gz"

SRC_URI[md5sum] = "8bb0966ff3458699c02fde3d5c799824"
SRC_URI[sha256sum] = "786698225e5cb43e8f061b78cfac1e0e7d48d370034ffdc518255207741c0b2a"

BBCLASSEXTEND="native"

S = "${WORKDIR}/File-Sync-${PV}"

inherit cpan
