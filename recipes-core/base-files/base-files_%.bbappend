# make sure root user has .profile and .bashrc so that it sources
# bash-completion
do_install_append () {
	install -m 0755 ${WORKDIR}/share/dot.profile ${D}${ROOT_HOME}/.profile
    echo "source /etc/bash_completion" > ${D}${ROOT_HOME}/.bashrc
}
