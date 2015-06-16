#!/usr/bin/bash

echo "############################################################"
echo "This is a script to build CLR upon the latest version of gcc"
echo "############################################################"



git_clone(){

    echo "############################################################"
    echo "git clone package"
    echo "############################################################"

    git clone --depth=1 git://kojiclear.jf.intel.com/packages/$1
}

build(){

    echo "############################################################"
    echo "Building"
    echo "############################################################"

    pushd $1
    if [ ! -f results/*x86_64.rpm ] ; then
        echo "No RPM"
        make
        rc=$?; if [[ $rc != 0 ]]; then echo $1 >> failing_repos.txt; fi

    fi
    popd
}

store_in_server(){

    echo "############################################################"
    echo "Storing"
    echo "############################################################"

    pushd $1
    sudo cp -rf results/*.rpm /usr/share/httpd/htdocs/repos_compiled_with_gcc5/
    popd
}
while read pkg
do 
    echo Package : $pkg
    #git_clone $pkg
    build $pkg
    if [[ $rc == 0 ]]; then store_in_server $pkg ; fi

done < $1
