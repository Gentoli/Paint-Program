// JNI.cpp : Defines the exported functions for the DLL application.
//

#include <jni.h>
#include <iostream>

#include <bitset>
#include <ctime>
#include <thread>

#include <windows.h>    
#include <windowsx.h>  


extern "C" {
	JNIEXPORT void JNICALL Java_ca_utoronto_utm_pointer_WindowsPointer_Init(JNIEnv *, jclass, jlong);
}


LRESULT CALLBACK PtrSetup(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam);
LRESULT CALLBACK WndProcProxy(HWND hwnd, UINT wm, WPARAM wParam, LPARAM lParam);


WNDPROC wndprocOrig;
JNIEnv *env,*windEnv;
jmethodID update, key;
jclass thisClass;
JavaVM *jvm;

void test() {
	//std::cout << "hello thread" << std::endl << std::flush;
}

JNIEXPORT void JNICALL Java_ca_utoronto_utm_pointer_WindowsPointer_Init(JNIEnv *e, jclass c, jlong lhWnd) {
	std::cout << "Hello JNI!\n";
	env = e;
	thisClass = c;
	env->GetJavaVM(&jvm);
	update = env->GetStaticMethodID(thisClass, "Update", "(IJIIIIII)V");
	key = env->GetStaticMethodID(thisClass, "KeyUpdate", "(IJIIC)V");
	std::thread first(test);
	wndprocOrig = (WNDPROC)SetWindowLongPtr((HWND)lhWnd, GWLP_WNDPROC, (LONG_PTR)PtrSetup);
	std::cout << "JNI1 Inititallized!\n";
	first.join();
}
LRESULT CALLBACK PtrSetup(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam) {
	/*JavaVM *jvm;
	env->GetJavaVM(&jvm);*/
	//jvm->AttachCurrentThread((void **)&windEnv, NULL);
	//jvm->DetachCurrentThread();

	RegisterPointerDeviceNotifications(hWnd, 0);
	EnableMouseInPointer(true);
	SetWindowLongPtr(hWnd, GWLP_WNDPROC, (LONG_PTR)WndProcProxy);
	std::cout << "JNI2 Inititallized!\n";
	return CallWindowProc(wndprocOrig, hWnd, message, wParam, lParam);
}
const int CONTROL = 1 << 1; //2
const int SHIFT = 1 << 0; //1
const int ALT = 1 << 3; //8
const int LBUTTON = 1 << 4; //16
const int RBUTTON = 1 << 2; //4
const int MBUTTON = 1 << 3; //8

const int JMOUSE_FIRST = 500;
const int JMOUSE_LAST = 507;
const int JMOUSE_PRESSED = 1 + JMOUSE_FIRST;
const int JMOUSE_RELEASED = 2 + JMOUSE_FIRST;
const int JMOUSE_MOVED = 3 + JMOUSE_FIRST;
const int JMOUSE_ENTERED = 4 + JMOUSE_FIRST;
const int JMOUSE_EXITED = 5 + JMOUSE_FIRST;
const int JKEY_PRESS = 401;
const int JKEY_RELEASE = 402;
void sendKeyUpdate(int eventId, WPARAM wParam);
void sendUpdate(int eventId, POINTER_INFO pointerInfo, int pressure);
jint getModifiers();


LRESULT CALLBACK WndProcProxy(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	//std::cout << message << "start" <<std::endl << std::flush;

	//if(message==586)
	//return CallWindowProc(wndprocOrig, hWnd, message, wParam, lParam);
	int eventId = 0;
	switch (message) {
	case WM_KEYDOWN:
	case WM_SYSKEYDOWN:
		eventId = JKEY_PRESS;
		goto key;
	case WM_KEYUP:
	case WM_SYSKEYUP:
		eventId = JKEY_RELEASE;
		goto key;
	key:
	{
		std::thread msg(sendKeyUpdate, eventId, wParam);
		msg.detach();
		
		return CallWindowProc(wndprocOrig, hWnd, message, wParam, lParam);
	}
	case WM_POINTERLEAVE:
		//std::cout << "leave" << std::endl << std::flush;
		eventId = JMOUSE_EXITED;
		goto process;
	case WM_POINTERDOWN:
		//std::cout << "down" << std::endl << std::flush;
		eventId = JMOUSE_PRESSED;
		goto process;
	case WM_POINTERUP:
		//std::cout << "up" << std::endl << std::flush;
		eventId = JMOUSE_RELEASED;
		goto process;
	case WM_POINTERENTER:
		//std::cout << "enter" << std::endl << std::flush;
		eventId = JMOUSE_ENTERED;
		goto process;
	case WM_POINTERUPDATE:
		//std::cout << "update" << std::endl << std::flush;
		eventId = JMOUSE_MOVED;
		goto process;
	process:
		{
			POINTER_PEN_INFO penInfo;
			POINTER_INFO pointerInfo;
			UINT32 pointerId = GET_POINTERID_WPARAM(wParam);
			POINTER_INPUT_TYPE pointerType = PT_POINTER;
			int pressure = 0;


			if (!GetPointerType(pointerId, &pointerType))
			{
				pointerType = PT_POINTER;
			}
			//std::cout << pointerInfo==nullptr << std::endl << std::flush;
			if (pointerType == PT_PEN) {
				if (GetPointerPenInfo(pointerId, &penInfo)) {
					pointerInfo = penInfo.pointerInfo;
					pressure = penInfo.pressure;
					//std::cout << penInfo.pressure << std::endl << std::flush;
				}
				else {
					break;
				}
					
			}
			else if (!GetPointerInfo(pointerId, &pointerInfo)) {
				break;
			}


			/*windEnv->CallStaticVoidMethod(thisClass, update,
				eventId, pointerInfo.dwTime, getModifiers(), pointerInfo.ptPixelLocation.x, pointerInfo.ptPixelLocation.y, pointerInfo.historyCount,
				pointerInfo.pointerId, pressure);*/
			
			std::thread msg(sendUpdate, eventId, pointerInfo, pressure);
			msg.detach();
			
			return CallWindowProc(wndprocOrig, hWnd, message, wParam, lParam);
		}
	}
	return CallWindowProc(wndprocOrig, hWnd, message, wParam, lParam);
}

void sendKeyUpdate(int eventId, WPARAM wParam) {
	JNIEnv *newEnv;
	jvm->AttachCurrentThread((void **)&newEnv, NULL);

	newEnv->CallStaticVoidMethod(thisClass, key,
		eventId, 0, getModifiers(), wParam, MapVirtualKey(wParam, MAPVK_VK_TO_CHAR));

	jvm->DetachCurrentThread();
}

void sendUpdate(int eventId, POINTER_INFO pointerInfo, int pressure) {
		JNIEnv *newEnv;
		jvm->AttachCurrentThread((void **)&newEnv, NULL);

		newEnv->CallStaticVoidMethod(thisClass, update,
			eventId, pointerInfo.dwTime, getModifiers(), pointerInfo.ptPixelLocation.x, pointerInfo.ptPixelLocation.y, pointerInfo.historyCount,
			pointerInfo.pointerId, pressure);

		jvm->DetachCurrentThread();
}

jint getModifiers() {
	int rtn = 0;
	rtn |= HIBYTE(GetKeyState(VK_CONTROL)) ? CONTROL : 0;
	rtn |= HIBYTE(GetKeyState(VK_MENU)) ? ALT : 0;
	rtn |= HIBYTE(GetKeyState(VK_SHIFT)) ? SHIFT : 0;
	rtn |= HIBYTE(GetKeyState(VK_LBUTTON)) ? LBUTTON : 0;
	rtn |= HIBYTE(GetKeyState(VK_RBUTTON)) ? RBUTTON : 0;
	rtn |= HIBYTE(GetKeyState(VK_MBUTTON)) ? RBUTTON : 0;
	return rtn;
}