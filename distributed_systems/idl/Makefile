all: 
	idlj -fall IDL_interface.idl
	idlj -fallTie IDL_interface.idl
	javac -cp ./ -d ./ *.java IDL_module/*.java

clean:
	find . -type f -name '*.class' -delete
	rm -rf IDL_module/ orb.db/
