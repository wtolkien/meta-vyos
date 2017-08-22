SUMMARY = "VyOS config library"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg"
SECTION = "vyos/config"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg.git;branch=current;protocol=https \
	   file://001-fix-namespace-ambiguity.patch \
	   file://002-remove-debian-apt-dependency.patch \
	   file://003-allow-perl-cross-compile.patch \
	   file://004-unionfs-fuse-path.patch \
	  "

# snapshot from Jul 10, 2017:
SRCREV = "3b8b2e11f322994cfa82fc6b09ce6af4ed715dfa"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = " \
	perl \
	flex-native \
	bison-native \
	pkgconfig-native \
	python3-setuptools-native \
	boost \
	glib-2.0 \
	"
RDEPENDS_${PN} += " \
	perl \
	libcap-bin \
	glibc-utils \
	boost \
	glib-2.0 \
	vyos-bash \
	vyos-cfg-system \
	vyos-config-migrate \
	"

FILES_${PN} += "/opt /usr/share /etc/apt"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit setuptools3 cpan autotools-brokensep python3native python3-dir \
	update-rc.d

# TODO: replace std::auto_ptr with std::unique_ptr - for now just turn
# compiler errors into warnings
CXXFLAGS_append = " -Wno-error=deprecated-declarations"

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --prefix=/opt/vyatta \
    --exec_prefix=/opt/vyatta \
	--sbindir=/opt/vyatta/sbin \
	--datadir=/opt/vyatta/share \
	--sysconfdir=/opt/vyatta/etc \
	--enable-unionfsfuse \
	"

# parallel make does not seem to work - possibly due to ylwrap?
PARALLEL_MAKE = "-j 1"

# this package contains python code in the 'python' subdirectory. This is handled
# by bitbake's 'setuptools3' class. However:
# a) the 'setuptools3' class needs to be inherited before the 'autotools' class so
#    that autotools routines have priority
# b) we append the setuputils3 routines here, after changing into the correct
#    subdirectory first
do_compile_append () {
	cd python;
	distutils3_do_compile
}

do_install_append () {
	cd python;
	distutils3_do_install
	cd ${S}

	# create an empty Debian apt-sources file
	install -d ${D}/etc/apt
	touch ${D}/etc/apt/sources.list

	# commit hooks
	install -d ${D}/etc/commit/pre-hooks.d
	install -d ${D}/etc/commit/post-hooks.d

	# create symlink for post commit hook
	ln -sf /opt/vyatta/sbin/vyatta-log-commit.pl \
		${D}/etc/commit/post-hooks.d/10vyatta-log-commit.pl

	# User pre/post-commit hook executors
	ln -s /opt/vyatta/sbin/vyos-user-precommit-hooks.sh \
		${D}/etc/commit/pre-hooks.d/99vyos-user-precommit-hooks
	ln -s /opt/vyatta/sbin/vyos-user-postcommit-hooks.sh \
		${D}/etc/commit/post-hooks.d/99vyos-user-postcommit-hooks
}



INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "vyatta-router"
INITSCRIPT_PARAMS_${PN} = "start 90 2 3 4 5 ."

# perform some post-installation actions, but only on target device, not at
# build time (some of these may be moved to build time, but 'setcap' and
# 'chgrp' may not work at build time...)
# Make sure to prepend variables with 'vy_' to avoid conflicts with bitbake
# variables
pkg_postinst_${PN} () {
	if [ x"$D" = "x" ]; then
		# capability stuff has to be done on target
		for vy_bin in my_cli_bin my_cli_shell_api; do
  			touch -ac /opt/vyatta/sbin/${vy_bin}
  			setcap cap_sys_admin=pe /opt/vyatta/sbin/${vy_bin}
		done
	else
   		exit 1
	fi
}
