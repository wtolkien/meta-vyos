SUMMARY = "VyOS config library"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg"
SECTION = "vyos/config"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg.git;branch=current;protocol=https \
	   file://002-remove-debian-apt-dependency.patch \
	   file://003-allow-perl-cross-compile.patch \
	   file://004-unionfs-fuse-path.patch \
	   file://005-depend-on-sshdgenkeys-service.patch \
	   file://006-fix-logrotate-permissions.patch \
	   file://007-no-invokerc-for-auth-logrotate.patch \
	   file://008-remove-obsolete-sysvstartpriority.patch \
	   file://010-startup-load-persistent-data-part.patch \
	  "

# snapshot from April 13, 2018:
SRCREV = "2a925cdc203cab2d8b8a3bd08ab6380f399b8bc9"

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
	file \
	tree \
	vyos-bash \
	vyos-cfg-system \
	vyos-config-migrate \
	"

FILES_${PN} += "/opt /usr/share /etc/apt"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
#inherit setuptools3
inherit cpan autotools-brokensep python3native python3-dir \
	systemd

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

VYOSCONFIGDIR = "${THISDIR}/files/config"

# parallel make does not seem to work - possibly due to ylwrap?
PARALLEL_MAKE = "-j 1"

# this package contains python code in the 'python' subdirectory. This is handled
# by bitbake's 'setuptools3' class. However:
# a) the 'setuptools3' class needs to be inherited before the 'autotools' class so
#    that autotools routines have priority
# b) we append the setuputils3 routines here, after changing into the correct
#    subdirectory first
#do_compile_append () {
#	cd python;
#	distutils3_do_compile
#}

do_install_append () {
#	cd python;
#	distutils3_do_install
#	cd ${S}

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

	# install default config
	install -d ${D}/opt/vyatta/etc
	if [ -f ${VYOSCONFIGDIR}/default-config-${MACHINE} ]; then
		install ${VYOSCONFIGDIR}/default-config-${MACHINE} ${D}/opt/vyatta/etc/config.boot.default
	else
		install ${VYOSCONFIGDIR}/default-config ${D}/opt/vyatta/etc/config.boot.default
	fi

	# install systemd service file
    install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${S}/debian/vyatta-cfg.vyatta-router.service \
		${D}${systemd_unitdir}/system/vyatta-router.service
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "vyatta-router.service"

pkg_postinst_ontarget_${PN} () {
	for vy_bin in my_cli_bin my_cli_shell_api; do
		touch -ac /opt/vyatta/sbin/${vy_bin}
  		setcap cap_sys_admin=pe /opt/vyatta/sbin/${vy_bin}
	done
}
