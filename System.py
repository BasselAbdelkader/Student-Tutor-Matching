from tkinter import *
from tkinter import ttk, messagebox
import requests
root_url = 'https://fit3077.com/api/v1'
users_url = root_url + "/user"

class Auth:
    _api_key = None
    _window = None
    _unameIn = None
    _pwrdIn = None
    _jwt = None
    _uname = None
    def __init__(self, api_key):
        self._api_key = api_key

    def login(self):
        self._window = Tk()
        c = ttk.Frame(self._window, padding=(5, 5, 12, 0))
        c.grid(column=0, row=0, sticky=(N, W, E, S))

        uname = ttk.Frame(c)
        uname.grid(row=0, columnspan=2, sticky=(W, E))
        lbl1 = Label(uname, text="Username: ")
        lbl1.grid(row=0, column=0, sticky=W)
        self._unameIn = ttk.Entry(uname, width=20)
        self._unameIn.grid(row=0, column=1, padx=5, sticky=(E))

        pwrd = ttk.Frame(c)
        pwrd.grid(row=1, columnspan=2, sticky=(W, E))
        lbl2 = Label(pwrd, text="Password: ")
        lbl2.grid(row=1, sticky=W)
        self._pwrdIn = ttk.Entry(pwrd, width=20)
        self._pwrdIn.grid(row=1, column=1, padx=5, sticky=(E))

        loginBtn = ttk.Button(c, text="Log In")
        loginBtn.grid(row=2, column=1, padx=5, sticky=E)
        loginBtn.configure(command=self.apiSignIn)
        self._pwrdIn.insert(0,"ronlow")
        self._unameIn.insert(0, "ronlow")
        self._window.mainloop()

        return self._jwt, self._uname

    def apiSignIn(self):
        users_login_url = users_url + "/login"
        self._uname = self._unameIn.get()
        # Note the post() method being used here.
        # A request body needs to be supplied to this endpoint, otherwise a 400 Bad Request error will be returned.
        response = requests.post(
            url=users_login_url,
            headers={'Authorization': self._api_key},
            params={'jwt': 'true'},  # Return a JWT so we can use it in Part 5 later.
            data={
                'userName': self._unameIn.get(),
                'password': self._pwrdIn.get()
                # The passwords are the same as username.
            }
        )
        if response.status_code == 200:
            json_data = response.json()
            self._jwt = json_data['jwt']
            self._window.destroy()
        else:
            messagebox.showwarning(title="Login Error", message="Invalid Login")

class UserCollection:

    users = []
    def __init__(self,api_key):

        response = requests.get(url=users_url, headers={ 'Authorization': api_key })

        if response.status_code == 200:
            json_data = response.json()
            for user in json_data:
                each = User(user)
                self.users.append(each)
        else:
            messagebox.showwarning(title="Load Users Error", message=response.reason)

    def getUserByUsername(self,uname):
        for user in self.users:
            if user.username == uname:
                return user
        return None

class User:
    id = None
    username = None
    given_name = None
    family_name = None
    is_student = False
    is_tutor =  False

    def __init__(self,json_data):
        self.id = json_data["id"]
        self.username = json_data["userName"]
        self.given_name = json_data["givenName"]
        self.family_name = json_data["familyName"]
        self.is_student = json_data["isStudent"]
        self.is_tutor = json_data["isTutor"]

    def __str__(self):
        return self.id + " " + self.username + " " + self.given_name + " " + self.family_name \
               + (" S " if self.is_student else "") + (" T " if self.is_tutor else "")

class Session:

    current_user = None
    window = None

    def __init__(self,current):
        self.current_user = current


    def start(self):
        print(self.current_user)
        # Init GUI elements
        # Create and grid the outer content frame
        self.window = Tk()
        c = ttk.Frame(self.window, padding=(5, 5, 12, 0))
        c.grid(row=0, columnspan=5, sticky=(N, S, W, E))

        # Title
        title = ttk.Frame(c)
        title.grid(row=0, column=0, sticky=(W, E))
        lbl1 = Label(title, text="Main Window")
        lbl1.grid(row=0, column=0, sticky=W)

        # Events List Elements
        lbl2 = Label(c, text="Open Bids")
        lbl2.grid(row=1, sticky=W)

        eventlist = Listbox(c, height=8, width=50, exportselection=False)
        eventlist.grid(row=2, columnspan=2, rowspan=8, sticky=(W, E))

        refreshBtn = ttk.Button(c, text="Refresh")
        refreshBtn.grid(row=2, column=2, padx=5, sticky=(N, E))

        newBidBtn = ttk.Button(c, text="Post Bid")
        newBidBtn.grid(row=3, column=2, padx=5, sticky=(N, E))


        # run the program
        self.window.mainloop()




class Application:

    API_KEY = "nwPqJThKp7jwCtf8McjrgTfWkdFmnJ"
    USERS = UserCollection(API_KEY)

    def __init__(self):

        a = Auth(self.API_KEY)
        jwt, uname = a.login()
        if jwt is not None:
            print(uname)
            s = Session(self.USERS.getUserByUsername(uname))
            s.start()




a = Application()