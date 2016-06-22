JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
	$(JC) -d . RemoteSortImpl.java
	rmic -d . RemoteSortImpl
CLASSES = \
	*.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) *~
