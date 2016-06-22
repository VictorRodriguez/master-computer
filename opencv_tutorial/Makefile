all:
	g++ video.c -o video `pkg-config --cflags --libs opencv`
	g++ image.c -o image `pkg-config --cflags --libs opencv`
	g++ camara.c -o camara `pkg-config --cflags --libs opencv`
	g++ face.c -o face `pkg-config --cflags --libs opencv`

clean:
	rm -rf video
	rm -rf image
	rm -rf camara
	rm -rf *~

