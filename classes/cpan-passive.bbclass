#
# This is for perl modules that use the old Makefile.PL build system
# It sets up all required environment variables, but does not actually
# build anyting - this is usefull if the perl module is part of a larger 
# package which handles building the perl module internally
#

inherit cpan-base perlnative

EXTRA_CPANFLAGS ?= ""
EXTRA_PERLFLAGS ?= ""

# Env var which tells perl if it should use host (no) or target (yes) settings
export PERLCONFIGTARGET = "${@is_target(d)}"

# Env var which tells perl where the perl include files are
export PERL_INC = "${STAGING_LIBDIR}${PERL_OWN_DIR}/perl/${@get_perl_version(d)}/CORE"
export PERL_LIB = "${STAGING_LIBDIR}${PERL_OWN_DIR}/perl/${@get_perl_version(d)}"
export PERL_ARCHLIB = "${STAGING_LIBDIR}${PERL_OWN_DIR}/perl/${@get_perl_version(d)}"
export PERLHOSTLIB = "${STAGING_LIBDIR_NATIVE}/perl-native/perl/${@get_perl_version(d)}/"


